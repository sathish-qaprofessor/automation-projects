#!/usr/bin/env node
const fs = require('fs');
const path = require('path');
const { merge } = require('mochawesome-merge');
const marge = require('mochawesome-report-generator');

(async () => {
  const reportDir = path.join(process.cwd(), 'cypress', 'reports', 'mochawesome');

  if (!fs.existsSync(reportDir)) {
    console.log('No mochawesome report directory found:', reportDir);
    process.exit(0);
  }

  function findJsonFiles(dir) {
    const entries = fs.readdirSync(dir, { withFileTypes: true });
    const result = [];
    for (const entry of entries) {
      const full = path.join(dir, entry.name);
      if (entry.isDirectory()) {
        result.push(...findJsonFiles(full));
      } else if (entry.isFile() && entry.name.endsWith('.json') && entry.name !== 'merged.json') {
        result.push(full);
      }
    }
    return result;
  }

  const filesPaths = findJsonFiles(reportDir);

  if (filesPaths.length === 0) {
    console.log('No mochawesome JSON files to merge.');
    process.exit(0);
  }

  try {
    const merged = await merge({ files: filesPaths });
    const outPath = path.join(reportDir, 'merged.json');

    if (typeof merged === 'string') {
      fs.writeFileSync(outPath, merged);
    } else {
      fs.writeFileSync(outPath, JSON.stringify(merged, null, 2));
    }

    console.log('Merged reports written to', outPath);

    await marge.create(outPath, { reportDir, reportFilename: 'index.html' });
    console.log('Report generated at', path.join(reportDir, 'index.html'));
  } catch (err) {
    console.error(err);
    process.exit(1);
  }
})();
